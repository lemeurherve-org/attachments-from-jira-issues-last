node {
  stage ('hello') {
    echo 'Hello World'
  }
}
node {
  stage ('various') {
    echo 'Foo'

    echo 'Bar'

    parallel('mars': {
      echo 'Hello Mars'
    },
    'jupiter': {
      echo 'Hello Jupiter'
    })

    echo 'Have a beer at foo bar'
  }
}
node {
  stage ('bye bye') {
    echo 'Bye bye World'
  }
}
