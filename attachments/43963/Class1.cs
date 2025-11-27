using NUnit.Framework;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ClassLibrary1
{
    [TestFixtureSource("testFixtureSource")]
    public class TestClass
    {
        public static string[] testFixtureSource = new string[] { "a", "b", "c" };
        public static int[] testMethodSource = new int[] { 1, 2, 3 };

        public TestClass(string s)
        {
        }

        [TestCaseSource("testMethodSource")]
        public void TestMethod(int i)
        {
        }

    }
}
