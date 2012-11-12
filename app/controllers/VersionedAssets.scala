package controllers

import play.api.mvc.PathBindable
import java.io.File
import play.api.Play

object VersionedAssets {
  def at(file: VersionedAsset) = Assets.at(file.path, file.file)
}

case class VersionedAsset(file: String, path: String = "public", versionParam: String = "v")

object VersionedAsset {

  implicit def pathBinder = new PathBindable[VersionedAsset] {
    def bind(key: String, value: String) = Right(VersionedAsset(value))

    def unbind(key: String, value: VersionedAsset) = {
      import Play.current
      val resourceName = Option(value.path + "/" + value.file).map(name => if (name.startsWith("/")) name else ("/" + name)).get
      val modified = Play.resource(resourceName).map { url =>
        url.getProtocol match {
          case file if file == "file" => new File(url.toURI).lastModified()
          case jar if jar == "jar" => new File(url.getFile.split("!/").head).lastModified()
        }
      }
      modified.map(value.file + "?" + value.versionParam + "=" + _).getOrElse(value.file)
    }
  }

  implicit def toVersionedAsset(path: String): VersionedAsset = VersionedAsset(path)
}

