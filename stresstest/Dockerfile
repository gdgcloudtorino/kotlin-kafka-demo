FROM loadimpact/k6
WORKDIR /app
COPY loadScenario.js .
ENTRYPOINT ["k6", "run" , "loadScenario.js"]