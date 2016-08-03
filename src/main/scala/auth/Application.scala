package auth

import com.twitter.finagle.{Http, Service}
import com.twitter.finagle.http
import com.twitter.util.{Await, Future}
import scala.collection.concurrent.TrieMap
import java.sql.{Connection, DriverManager, ResultSet};
import scala.util.control.Breaks._
import scalikejdbc._
import java.nio.charset.Charset

object Application extends App {
  classOf[org.postgresql.Driver]
  val con_st = "jdbc:postgresql://172.17.0.3:5432/postgres?user=postgres&password=111"

  def sti(list: String): Int = {
    var res = Math.pow(10,(list.length)-1).toInt
    var r = 0
    list.foreach(x => {
      r = r + res * (x.toInt - 48)
      res = res/10
    })
    r
  }

  val service = new Service[http.Request, http.Response] {
    val kv = TrieMap.empty[String,String]



    def apply(req: http.Request): Future[http.Response] = {
      Future {
        val conn = DriverManager.getConnection(con_st)
        val key = req.uri
        val value = req.contentString
        val resp = {
          http.Response(req.version, http.Status.Ok)
        }
        req.method match{
          case http.Method.Get => {
            kv.get(key) match{
              case None =>
                resp.status_=(http.Status.NotFound)
              case Some(v) =>
                resp.contentString_= (v)
            }

          }
          case http.Method.Post =>
            {
              try {
                val stm = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)

                val rs = stm.executeQuery("SELECT * from acc")
                breakable {
                  while (rs.next) {
                    if (rs.getString("login") == key && rs.getString("pass") == value.toString) {
                      val prep = conn.prepareStatement("UPDATE ACC SET val = val + 1")
                      prep.executeUpdate()

                      println(sti(rs.getString("val"))+1)
                      break
                    }
                  }
                }
              } finally {
                conn.close()
              }
            }
        }
        resp
      }
    }
  }

  val server = Http.serve("127.0.0.1:8885", service)




  Await.ready(server)
}