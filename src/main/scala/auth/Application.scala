package auth

import java.net.InetSocketAddress

import com.twitter.finagle.builder.ServerBuilder
import com.twitter.finagle.http.Http


object Application extends App {
  val MAX_RESPONSE_SIZE = "100.megabyte"

  val servise = new HttpService
  ServerBuilder()
    .name("status-service")
    //    .bindTo(new InetSocketAddress(config.host, config.port))
    .bindTo(new InetSocketAddress("127.0.0.1", 6666))
    .codec(Http())
    .build(servise)
}