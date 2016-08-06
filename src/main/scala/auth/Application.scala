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


  val service = new Service[http.Request, http.Response] {
    val kv = TrieMap.empty[String, String]


    def apply(req: http.Request): Future[http.Response] = {
      //println("asdasdasdsdadasdadasd")
      Future {
        val key = req.uri
        val value = req.contentString
        val resp = {
          http.Response(req.version, http.Status.Ok)
        }
        req.method match {
          case http.Method.Get => {
            kv.get(key) match {
              case None =>
                resp.status_=(http.Status.NotFound)
              case Some(v) =>
                resp.contentString_=(v)
            }

          }
          case http.Method.Post => {
            val str = DBui.amount_connection(key.substring(1),value)
            str match {
              case -1 =>
                resp.status_= (http.Status.NotFound)
              case _ =>
                resp.contentString_=(str.toString)
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