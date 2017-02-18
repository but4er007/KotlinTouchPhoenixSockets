# This file is responsible for configuring your application
# and its dependencies with the aid of the Mix.Config module.
#
# This configuration file is loaded before any dependency and
# is restricted to this project.
use Mix.Config

# General application configuration
config :java_test,
  ecto_repos: [JavaTest.Repo]

# Configures the endpoint
config :java_test, JavaTest.Endpoint,
  url: [host: "localhost"],
  secret_key_base: "f7ETfdxAW7BLGUX/RSSSL6ondRAM5/3Hf+N10sO5ImyVqoP48MDG/SaVVdVuCpL/",
  render_errors: [view: JavaTest.ErrorView, accepts: ~w(html json)],
  pubsub: [name: JavaTest.PubSub,
           adapter: Phoenix.PubSub.PG2]

# Configures Elixir's Logger
config :logger, :console,
  format: "$time $metadata[$level] $message\n",
  metadata: [:request_id]

# Import environment specific config. This must remain at the bottom
# of this file so it overrides the configuration defined above.
import_config "#{Mix.env}.exs"
