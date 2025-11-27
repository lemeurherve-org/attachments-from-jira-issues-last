using System;
using NUnit.Framework;

namespace GetBrowsersTest
{
    [TestFixture]
    class BrowserNamesTest1
    {        
        [Test]
        public void ShowBrowsers( [ValueSource("MyBrowserNames")] string browser)
        {
            Console.WriteLine(browser);
            Assert.Pass();
        }

        [Test]
        public void AnotherTest([ValueSource("MyBrowserNames")] string browser)
        {
            Console.WriteLine(browser);
            Assert.Pass();
        }

        [Test]
        public void FailTest([ValueSource("MyBrowserNames")] string browser)
        {
            Console.WriteLine(browser);
            Assert.Fail();
        }

        public static string[] MyBrowserNames
        {
            get { return new[] {"IE", "FireFox", "Safari"}; }
        }
    }
}
