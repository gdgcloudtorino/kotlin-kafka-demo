import http from 'k6/http';
export let options = {
    vus: 10,
    iterations: 100,
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