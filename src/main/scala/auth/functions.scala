package auth

/**
  * Created by evgeniy on 05.08.16.
  */
object functions {
  def sti(list: String): Int = {
    var res = Math.pow(10, (list.length) - 1).toInt
    var r = 0
    list.foreach(x => {
      r = r + res * (x.toInt - 48)
      res = res / 10
    })
    r
  }
}
