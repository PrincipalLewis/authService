package auth

import com.twitter.finagle.Service
import com.twitter.finagle.http._
import com.twitter.util.{ Future, Return, Throw, Try }
import net.liftweb.json.DefaultFormats
import net.liftweb.json.Serialization._

import org.slf4j.LoggerFactory

class HttpService extends Service[Request, Response] {

  private val log = LoggerFactory.getLogger(classOf[HttpService])
  implicit val formats = DefaultFormats


  override def apply(request: Request): Future[Response] = {
    val handlers = request.method match {
      case Method.Get =>
        log.debug(s"GET METHOD INVOKE")
        buildGetRequest(request)
      //      case Method.Post =>
      case method =>
        val err = new NotImplementedError(s"Not supported http method $method.")
        log.error(s"error $err")
        Error(err)
    }
    handlers
  }

  def buildGetRequest(request: Request): Future[Response] = {
    request.path.toLowerCase match {
      case "/authorization" =>
        authorization()
      case something => NotFound(something)
    }
  }

  def authorization(): Future[Response] = {
    getDbCredential.flatMap({
      case Some(str: String) =>
        jsonResponse(str)
      case None =>
        val unknownUser = "Пользователь с таким логином и паролем не существует"
        jsonResponse(unknownUser)
    })
  }


  /**
   * пока что моковый метод, реализация будет к дб
   * @return
   */
  def getDbCredential: Future[Option[String]] = {
    Future(Some("JEKA XUY"))
  }

  /**
   * Ответ с кодом 200 и телом с переданным контентом
   * @param content
   * @param mediaType
   * @return
   */
  def Ok(content: String, mediaType: String = "text/plain"): Future[Response] = {
    val response = Response(Status.Ok)
    response.setContentType(mediaType)
    response.setContentString(content)
    Future(response)
  }

  /**
   * Ответ с кодом 500 и телом с сообщением из ошибки
   * @param e
   * @return
   */
  def Error(e: Throwable): Future[Response] = {
    val response = Response(Status.InternalServerError)
    response.setContentType("text/plain")
    response.setContentString(e.getMessage)
    Future(response)
  }

  def NotFound(path: String): Future[Response] = {
    val response = Response(Status.NotFound)
    response.setContentType("text/plain")
    response.setContentString(path)
    Future(response)
  }

  private def pathToPattern(path: String) = {
    path.replaceAll(":[a-zA-Z]+", "(.+)")
  }

  private def jsonResponse(content: AnyRef): Future[Response] = {
    Try(writePretty(content)) match {
      case Return(json) =>
        Ok(json, "application/json")
      case Throw(e) =>
        Error(e)
    }
  }
}
