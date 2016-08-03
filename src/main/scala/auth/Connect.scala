package auth

/**
  * Created by evgeniy on 03.08.16.
  */
import java.sql.{Connection, DriverManager, ResultSet}
import scalikejdbc._

object Connect {
  classOf[org.postgresql.Driver]
  val con_st = "jdbc:postgresql://172.17.0.2:5432/postgre?user=DB_USER"
  val conn = DriverManager.getConnection(con_str)
}
