package auth

/**
  * Created by evgeniy on 03.08.16.
  */

import java.sql.{Connection, DriverManager, ResultSet, Statement}

import com.twitter.util.Future
import scalikejdbc._

import scala.util.control.Breaks._

object Connect {
  /**
    * Подключение к базе данных "postgresql"
    * Драйвер для подключения к базе данных - org.postgresql.Driver
    * Адрес базы данных - jdbc:postgresql://172.17.0.3:5432/postgres
    * Пользователь базы данных - postgres
    * Пароль пользователя - 111
    */
    def connection (): Connection  = {
      val driver = "org.postgresql.Driver"
      val url = "jdbc:postgresql://172.17.0.2:5432/postgres"
      val username = "postgres"
      val password = "111"
      Class.forName (driver)
      val conn = DriverManager.getConnection (url, username, password)
      conn
    }
}
