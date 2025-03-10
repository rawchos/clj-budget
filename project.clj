(defproject clj-budget "0.1.0-SNAPSHOT"
  :description "Clojure project for manipulating a budgets.edn file."
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [cljfmt/cljfmt       "0.6.4"]
                 [cheshire/cheshire   "5.9.0"]]
  :main ^:skip-aot clj-budget.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}}
  :aliases {"cli" ["run" "-m" clj-budget.command-line]})
