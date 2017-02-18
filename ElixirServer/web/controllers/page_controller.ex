defmodule JavaTest.PageController do
  use JavaTest.Web, :controller

  def index(conn, _params) do
    render conn, "index.html"
  end
end
