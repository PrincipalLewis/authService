package auth

import java.sql.{Connection, PreparedStatement, ResultSet}

import com.twitter.util.Future

import scala.util.control.Breaks._

/**
  * Created by evgeniy on 05.08.16.
  */
object DBui {
  /**
    * Вывод количестова входов по login pass
    * @param userLogin
    * @param password
    */
  def amount_connection(userLogin: String, password: String): Int = {
    var conn: Connection = null
    var amount = 0
    try {
      conn = Connect.connection()
      val sql_command = "SELECT * FROM acc WHERE login = '" + userLogin + "'"
      val stm = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)
      val rs = stm.executeQuery(sql_command)
      var prep: PreparedStatement = null

      if (rs.next) {
        if(rs.getString("pass") == password) {
          prep = conn.prepareStatement("UPDATE ACC SET val = val + 1")
          prep.executeUpdate()
          amount = functions.sti(rs.getString("val")) + 1
        } else {
          amount = -1
        }

      }
      else {
        prep = conn.prepareStatement("INSERT INTO acc VALUES ('" + userLogin + "','" + password + "', 0)")
        prep.execute()
        amount = 0
      }
    } finally {

    }
    conn.close()
    amount
  }
}
