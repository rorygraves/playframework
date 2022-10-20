/*
 * Copyright (C) from 2022 The Play Framework Contributors <https://github.com/playframework>, 2011-2021 Lightbend Inc. <https://www.lightbend.com>
 */

package javaguide.http;

// #default
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import java.util.concurrent.CompletionStage;

import java.lang.reflect.Method;

public class JavaActionCreator implements play.http.ActionCreator {
  @Override
  public Action createAction(Http.Request request, Method actionMethod) {
    return new Action.Simple() {
      @Override
      public CompletionStage<Result> call(Http.Request req) {
        return delegate.call(req);
      }
    };
  }
}
// #default
