package auth

/**
  * Created by evgeniy on 03.08.16.
  */
import java.sql.{Connection, DriverManager, ResultSet}
import scalikejdbc._

object Connect {
  classOf[org.postgresql.Driver]
  val con_st = "jdbc:postgresql://172.17.0.3:5432/pgs_w?user=postgres&password=111"
  val conn = DriverManager.getConnection(con_st)
  try {
    val stm = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)

    val rs = stm.executeQuery("SELECT * from acc")

    while(rs.next) {
      println(rs.getString("quote"))
    }
  } finally {
    conn.close()
  }
}
