/*
 * Copyright (C) from 2022 The Play Framework Contributors <https://github.com/playframework>, 2011-2021 Lightbend Inc. <https://www.lightbend.com>
 */

package play.it.http.parsing

import org.apache.pekko.stream.scaladsl.Source
import org.apache.pekko.stream.Materializer
import org.apache.pekko.util.ByteString
import play.api.mvc.PlayBodyParsers
import play.api.test._

class ByteStringBodyParserSpec extends PlaySpecification {
  "The ByteString body parser" should {
    def parsers(implicit mat: Materializer) = PlayBodyParsers()
    def parser(implicit mat: Materializer)  = parsers.byteString.apply(FakeRequest())

    "parse single byte string bodies" in new WithApplication() {
      override def running() = {
        await(parser.run(ByteString("bar"))) must beRight(===(ByteString("bar")))
      }
    }

    "parse multiple chunk byte string bodies" in new WithApplication() {
      override def running() = {
        await(
          parser.run(
            Source(List(ByteString("foo"), ByteString("bar")))
          )
        ) must beRight(===(ByteString("foobar")))
      }
    }

    "refuse to parse bodies greater than max length" in new WithApplication() {
      override def running() = {
        val parser = parsers.byteString(4).apply(FakeRequest())
        await(
          parser.run(
            Source(List(ByteString("foo"), ByteString("bar")))
          )
        ) must beLeft
      }
    }
  }
}
