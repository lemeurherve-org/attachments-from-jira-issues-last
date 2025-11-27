def host = "api.github.com"
def url = "https://api.github.com/"
def proxyConfig = jenkins.model.Jenkins.get().proxy

println "\n### Connection via okhttp3 ###\n"

okhttp3.OkHttpClient.Builder clientBuilder = new okhttp3.OkHttpClient().newBuilder()
clientBuilder.proxy(proxyConfig == null ? Proxy.NO_PROXY : proxyConfig.createProxy(host))
clientBuilder.proxySelector(new io.jenkins.plugins.okhttp.api.internals.JenkinsProxySelector(Jenkins.get().proxy))
okhttp3.OkHttpClient okhttp3Client = clientBuilder.build()
okhttp3.Request okhttp3Request = new okhttp3.Request.Builder().url(url).build()
okhttp3.Response okhttp3Response = okhttp3Client.newCall(okhttp3Request).execute()

def responseBody = null
try {
  responseBody = okhttp3Response.body()
  println (responseBody.string())
} finally {
  responseBody.close()
}
println (okhttp3Response.handshake().cipherSuite())
println (okhttp3Response.handshake().tlsVersion())

sleep 30000
/********************************
 * Delete Proxy pod and Service *
 ********************************/

okhttp3Request = new okhttp3.Request.Builder().url(url).build()
okhttp3Response = okhttp3Client.newCall(okhttp3Request).execute()

responseBody = null
try {
  responseBody = okhttp3Response.body()
  println (responseBody.string())
} finally {
  responseBody.close()
}
println (okhttp3Response.handshake().cipherSuite())
println (okhttp3Response.handshake().tlsVersion())