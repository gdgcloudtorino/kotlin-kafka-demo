import http from 'k6/http';
export let options = {
    vus: process.env["VUS"],
    iterations: process.env["ITERATIONS"],
};
export default function () {
  var url = 'http://kafka-demo:8080/message';
  var payload = "TEST";
  var params = {
    headers: {
      'Content-Type': 'text/plain',
    },
  };
  http.post(url, payload, params);
}