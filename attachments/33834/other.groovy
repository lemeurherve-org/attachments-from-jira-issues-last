
def defaultString() { "default string" }
def testEcho(String testString = defaultString()) {
  echo "in inner testEcho: ${testString}"
}

return this;

