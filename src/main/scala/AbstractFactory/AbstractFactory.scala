package AbstractFactory

import scala.collection.mutable.ArrayBuffer

abstract class Item(s: String) {
  protected val caption = s

  def makeHTML(): String
}

abstract class Page(t: String, a: String) {
  protected val title = t
  protected val author = a
  protected val content = new ArrayBuffer[Item]()

  def add(item: Item): Unit = {
    content += item
  }

  def output(): Unit = {
    println(this.makeHTML())
  }

  // 抽象メソッド
  def makeHTML(): String
}

class ListPage(title: String, author: String) extends Page(title, author) {
  override def makeHTML(): String = {
    val buffer = new StringBuffer()
    buffer.append("<html><head><title>" + title + "</title></head>\n")
    buffer.append("<body>\n")
    buffer.append("<h1>" + title + "</h1>\n")
    buffer.append("<ul>\n")
    val it = content.iterator
    while (it.hasNext) {
      val item = it.next
      buffer.append(item.makeHTML())
    }
    buffer.append("</ul>\n")
    buffer.append("<hr><address>" + author + "</address>")
    buffer.append("</body></html>\n")
    buffer.toString()
  }
}

class TablePage(title: String, author: String) extends Page(title, author) {
  override def makeHTML(): String = {
    val buffer = new StringBuffer()
    buffer.append("<html><head><title>" + title + "</title></head>\n")
    buffer.append("<body>\n")
    buffer.append("<h1>" + title + "</h1>\n")
    buffer.append("<table witdh=\"80%\" border=\"3\">\n")
    val it = content.iterator
    while (it.hasNext) {
      val item = it.next
      buffer.append("<tr>" + item.makeHTML() + "</tr>")
    }
    buffer.append("</table>\n")
    buffer.append("<hr><address>" + author + "</address>")
    buffer.append("</body></html>\n")
    buffer.toString()
  }
}


abstract class Link(s: String, u: String) extends Item(s) {
  protected val url = u
}

class TableLink(caption: String, url: String) extends Link(caption, url) {
  override def makeHTML(): String =
    "<td><a href=\"" + url + "\">" + caption + "</a></td>\n"
}

class ListLink(caption: String, url: String) extends Link(caption, url) {
  override def makeHTML(): String =
    " <li><a href=\"" + url + "\">" + caption + "</a></li>\n"
}

abstract class Tray(s: String) extends Item(s) {
  protected val tray = new ArrayBuffer[Item]()

  def add(item: Item): tray.type =
    tray += item
}

class ListTray(caption: String) extends Tray(caption) {
  def makeHTML(): String = {
    val buffer = new StringBuffer()
    buffer.append("<li>\n")
    buffer.append(caption + "\n")
    buffer.append("<ul>\n")
    val it = tray.iterator
    while (it.hasNext) {
      val item = it.next
      buffer.append(item.makeHTML)
    }
    buffer.append("</ul>\n")
    buffer.append("</li>\n")
    buffer.toString()
  }
}

class TableTray(caption: String) extends Tray(caption) {
  override def makeHTML(): String = {
    val buffer = new StringBuffer()
    buffer.append("<td>")
    buffer.append("<table width=\"100%\" border=\"1\"><tr>")
    buffer.append("<td bgcolor=\"#cccccc\" align=\"center\" colspan=\"" + tray.size + "\"><b>" + caption + "</b></td>")
    buffer.append("</tr>\n")
    buffer.append("<tr>")
    val it = tray.iterator
    while (it.hasNext) {
      val item = it.next
      buffer.append(item.makeHTML())
    }
    buffer.append("</tr></table>")
    buffer.append("</td>")
    buffer.toString
  }
}

abstract class Factory {
  def createLink(caption: String, url: String): Link

  def createTray(caption: String): Tray

  def createPage(title: String, author: String): Page
}

class TableFactory extends Factory {
  def createLink(caption: String, url: String): Link =
    new TableLink(caption, url)

  def createTray(caption: String): Tray =
    new TableTray(caption)

  def createPage(title: String, author: String): Page =
    new TablePage(title, author)
}


class ListFactory extends Factory {

  override def createLink(caption: String, url: String): Link =
    new ListLink(caption, url)

  override def createTray(caption: String): Tray =
    new ListTray(caption)

  override def createPage(title: String, author: String): Page =
    new ListPage(title, author)
}

object Factory {
  def getFactory(c: String): Factory = {
    val classname = c
    var factory: Factory = null
    try {
      factory = Class.forName(classname).newInstance().asInstanceOf[Factory]
    } catch {
      case e: ClassNotFoundException =>
        e.printStackTrace()
      case e: Exception =>
        e.printStackTrace()
    }
    factory
  }
}