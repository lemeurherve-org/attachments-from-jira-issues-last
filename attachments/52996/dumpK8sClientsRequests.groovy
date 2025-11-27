println "K8s Clients Summary\n----------"
println "* ${org.csanchez.jenkins.plugins.kubernetes.KubernetesClientProvider.clients.asMap().size()} active clients"
println "* ${org.csanchez.jenkins.plugins.kubernetes.KubernetesClientProvider.expiredClients.size()} expired clients"
println "* ${org.csanchez.jenkins.plugins.kubernetes.KubernetesClientProvider.runningCallsCount} running calls"
println "* ${org.csanchez.jenkins.plugins.kubernetes.KubernetesClientProvider.queuedCallsCount} queued calls"
println ""

println "Active K8s Clients\n----------"
org.csanchez.jenkins.plugins.kubernetes.KubernetesClientProvider.clients.asMap().each { _, client ->
  def k8sClient = client.client
  def httpClient = k8sClient.httpClient
  def dispatcher = httpClient.dispatcher()
  println k8sClient
  def runningCalls = dispatcher.runningCalls()
  println "* RUNNING " + runningCalls.size()
  runningCalls.each { call ->
    println "  * " + call.request()
  }
  def queuedCalls = dispatcher.queuedCalls()
  println "* QUEUED " + queuedCalls.size()
  queuedCalls.each { call ->
    println "  * " + call.request()
  }
  println "* SETTINGS "
  println "  * Connect Timeout (ms): " + httpClient.connectTimeoutMillis()
  println "  * Read Timeout (ms): " + httpClient.readTimeoutMillis()
  println "  * Write Timeout (ms): " + httpClient.writeTimeoutMillis()
  println "  * Ping Interval (ms): " + httpClient.pingIntervalMillis()
  println "  * Retry on failure " + httpClient.retryOnConnectionFailure()
  println "  * Max Concurrent Requests: " + dispatcher.getMaxRequests()
  println "  * Max Concurrent Requests per Host: " + dispatcher.getMaxRequestsPerHost()
  def connectionPool = httpClient.connectionPool()
  println "* CONNECTION POOL "
  println "  * Active Connection " + connectionPool.connectionCount()
  println "  * Idle Connection " + connectionPool.idleConnectionCount()
  println ""
}

println "Expired K8s Clients\n----------"
org.csanchez.jenkins.plugins.kubernetes.KubernetesClientProvider.expiredClients.each { client ->
  def k8sClient = client.client
  def httpClient = k8sClient.httpClient
  def dispatcher = httpClient.dispatcher()
  println k8sClient
  println ""
  def runningCalls = dispatcher.runningCalls()
  println "* RUNNING " + runningCalls.size()
  runningCalls.each { call ->
    println "  * " + call.request()
  }
  def queuedCalls = dispatcher.queuedCalls()
  println "* QUEUED " + queuedCalls.size()
  queuedCalls.each { call ->
    println "  * " + call.request()
  }
  println "* SETTINGS "
  println "  * Connect Timeout (ms): " + httpClient.connectTimeoutMillis()
  println "  * Read Timeout (ms): " + httpClient.readTimeoutMillis()
  println "  * Write Timeout (ms): " + httpClient.writeTimeoutMillis()
  println "  * Ping Interval (ms): " + httpClient.pingIntervalMillis()
  println "  * Retry on failure " + httpClient.retryOnConnectionFailure()
  println "  * Max Concurrent Requests: " + dispatcher.getMaxRequests()
  println "  * Max Concurrent Requests per Host: " + dispatcher.getMaxRequestsPerHost()
  def connectionPool = httpClient.connectionPool()
  println "* CONNECTION POOL "
  println "  * Active Connection " + connectionPool.connectionCount()
  println "  * Idle Connection " + connectionPool.idleConnectionCount()
}
return