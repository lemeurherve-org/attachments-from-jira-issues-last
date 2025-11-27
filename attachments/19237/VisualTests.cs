using System;
using System.Collections.Generic;
using System.Text;
using NUnit.Framework;

namespace ModelCenterTest
{
   [TestFixture]
   class VisualTests
   {
      private MSScriptControl.IScriptControl sc;

      [SetUp]
      public void SetUp()
      {
         sc = new MSScriptControl.ScriptControl();
      }

      [TearDown]
      public void TearDown()
      {
         if (sc != null)
         {
            sc.Reset();
            System.Runtime.InteropServices.Marshal.FinalReleaseComObject(sc);
         }
         sc = null;
      }

      [Test, TestCaseSource("testList_Priority_B")]
      public void VisualTest_Priority_B(string file)
      {
          commonfns func = new commonfns();
         sc.Language = "VBScript";
         string rpath = file.Substring(0, file.LastIndexOf('\\'));
         sc.ExecuteStatement("TestRunnerPath=\"" + rpath + "\"");
         string code;
         string ScriptFileText;
         string ScriptFileTextPath;

         ScriptFileTextPath = func.getActualFilePath("..\\..\\..\\TestCases\\AutoTests\\VisualTest\\ModelCenter\\Command_Lib\\Include_Files\\InitializeGlobalVar.inc");
         ScriptFileText = System.IO.File.ReadAllText(ScriptFileTextPath);

         string testBeginning;

         testBeginning = "Dim lib, sh, cst " + Environment.NewLine;
         testBeginning = testBeginning + "Set cst = CreateObject( \"MCConstants.wsc\" ) " + Environment.NewLine;
         testBeginning = testBeginning + "Set sh = CreateObject( \"WScript.Shell\" )" + Environment.NewLine ;
         testBeginning = testBeginning + "Set lib = CreateObject( \"TestFunctions.Functions.WSC\" )" + Environment.NewLine;
         testBeginning = testBeginning + "testName = \"" + file.Substring(file.LastIndexOf('\\') + 1) + "\" " + Environment.NewLine;
         testBeginning = testBeginning + "lib.SetTestName testName " + Environment.NewLine;



         code = ScriptFileText + testBeginning + System.IO.File.ReadAllText(file);

         sc.Timeout = 300000;
         try
         {
            sc.AddCode(code);
         }
         catch (Exception e)
         {
            string msg = e.ToString() + "\n" +
               "Description: " + sc.Error.Description + "\n" +
               "Source: " + sc.Error.Source + "\n" +
               "Line: " + sc.Error.Line + "\n" +
               "Text: " + sc.Error.Text + "\n" +
               "Number: " + sc.Error.Number;
            Assert.Fail(msg);
         }
      }

      static public IEnumerable<TestCaseData> testList_Priority_B
      {
         get
         {
            commonfns func=new commonfns();
            string testDir = func.getActualDirectoryPath("..\\..\\..\\TestCases\\AutoTests\\VisualTest\\ModelCenter\\Test_Case_Files\\Priority_ B");
            IEnumerable<string> files = System.IO.Directory.GetFiles(testDir, "*.vbs");
            foreach (string f in files)
            {
               string testName = f.Substring(f.LastIndexOf('\\')+1);
               testName = testName.Substring(0, testName.Length - 4);
               yield return new TestCaseData(f).SetName(testName);
            }
         }
      }



      [Test, TestCaseSource("testList_Priority_A")]
      public void VisualTest_Priority_A(string file)
      {
          commonfns func = new commonfns();
          sc.Language = "VBScript";
          string rpath = file.Substring(0, file.LastIndexOf('\\'));
          sc.ExecuteStatement("TestRunnerPath=\"" + rpath + "\"");
          string code;
          string ScriptFileText;
          string ScriptFileTextPath;

          ScriptFileTextPath = func.getActualFilePath("..\\..\\..\\TestCases\\AutoTests\\VisualTest\\ModelCenter\\Command_Lib\\Include_Files\\InitializeGlobalVar.inc");
          ScriptFileText = System.IO.File.ReadAllText(ScriptFileTextPath);

          string testBeginning;

          testBeginning = "Dim lib, sh, cst " + Environment.NewLine;
          testBeginning = testBeginning + "Set cst = CreateObject( \"MCConstants.wsc\" ) " + Environment.NewLine;
          testBeginning = testBeginning + "Set sh = CreateObject( \"WScript.Shell\" )" + Environment.NewLine;
          testBeginning = testBeginning + "Set lib = CreateObject( \"TestFunctions.Functions.WSC\" )" + Environment.NewLine;
          testBeginning = testBeginning + "testName = \"" + file.Substring(file.LastIndexOf('\\') + 1) + "\" " + Environment.NewLine;
          testBeginning = testBeginning + "lib.SetTestName testName " + Environment.NewLine;



          code = ScriptFileText + testBeginning + System.IO.File.ReadAllText(file);

          //also increased the timeout for this section, as at least one test needs it. 
          sc.Timeout = 900000;
          try
          {
              sc.AddCode(code);
          }
          catch (Exception e)
          {
              string msg = e.ToString() + "\n" +
                 "Description: " + sc.Error.Description + "\n" +
                 "Source: " + sc.Error.Source + "\n" +
                 "Line: " + sc.Error.Line + "\n" +
                 "Text: " + sc.Error.Text + "\n" +
                 "Number: " + sc.Error.Number;
              Assert.Fail(msg);
          }
      }

      static public IEnumerable<TestCaseData> testList_Priority_A
      {
          get
          {
              commonfns func = new commonfns();
              string testDir = func.getActualDirectoryPath("..\\..\\..\\TestCases\\AutoTests\\VisualTest\\ModelCenter\\Test_Case_Files\\Priority_A");
              IEnumerable<string> files = System.IO.Directory.GetFiles(testDir, "*.vbs");
              foreach (string f in files)
              {
                  string testName = f.Substring(f.LastIndexOf('\\') + 1);
                  testName = testName.Substring(0, testName.Length - 4);
                  yield return new TestCaseData(f).SetName(testName);
              }
          }
      }



      [Test, TestCaseSource("testList_Priority_C")]
      public void VisualTest_Priority_C(string file)
      {
          commonfns func = new commonfns();
          sc.Language = "VBScript";
          string rpath = file.Substring(0, file.LastIndexOf('\\'));
          sc.ExecuteStatement("TestRunnerPath=\"" + rpath + "\"");
          string code;
          string ScriptFileText;
          string ScriptFileTextPath;

          ScriptFileTextPath = func.getActualFilePath("..\\..\\..\\TestCases\\AutoTests\\VisualTest\\ModelCenter\\Command_Lib\\Include_Files\\InitializeGlobalVar.inc");
          ScriptFileText = System.IO.File.ReadAllText(ScriptFileTextPath);

          string testBeginning;

          testBeginning = "Dim lib, sh, cst " + Environment.NewLine;
          testBeginning = testBeginning + "Set cst = CreateObject( \"MCConstants.wsc\" ) " + Environment.NewLine;
          testBeginning = testBeginning + "Set sh = CreateObject( \"WScript.Shell\" )" + Environment.NewLine;
          testBeginning = testBeginning + "Set lib = CreateObject( \"TestFunctions.Functions.WSC\" )" + Environment.NewLine;
          testBeginning = testBeginning + "testName = \"" + file.Substring(file.LastIndexOf('\\') + 1) + "\" " + Environment.NewLine;
          testBeginning = testBeginning + "lib.SetTestName testName " + Environment.NewLine;



          code = ScriptFileText + testBeginning + System.IO.File.ReadAllText(file);
        // increased timeout here, because some of the tests in this group needed more time to go about their business properly
            // - used to be done on an individual basis, but we don't seem to have that level of fine-tuning here.
            // - so long as things work, the timeout really shouldn't be fully used anyway.
          sc.Timeout = 1800000;
          try
          {
              sc.AddCode(code);
          }
          catch (Exception e)
          {
              string msg = e.ToString() + "\n" +
                 "Description: " + sc.Error.Description + "\n" +
                 "Source: " + sc.Error.Source + "\n" +
                 "Line: " + sc.Error.Line + "\n" +
                 "Text: " + sc.Error.Text + "\n" +
                 "Number: " + sc.Error.Number;
              Assert.Fail(msg);
          }
      }

      static public IEnumerable<TestCaseData> testList_Priority_C
      {
          get
          {
              commonfns func = new commonfns();
              string testDir = func.getActualDirectoryPath("..\\..\\..\\TestCases\\AutoTests\\VisualTest\\ModelCenter\\Test_Case_Files\\Priority_ C");
              IEnumerable<string> files = System.IO.Directory.GetFiles(testDir, "*.vbs");
              foreach (string f in files)
              {
                  string testName = f.Substring(f.LastIndexOf('\\') + 1);
                  testName = testName.Substring(0, testName.Length - 4);
                  yield return new TestCaseData(f).SetName(testName);
              }
          }
      }

      /*
             [Test, TestCaseSource("testList_Interactive_Tests_Priority_A")]
             public void VisualTest_Interactive_Tests_Priority_A(string file)
            {
                commonfns func=new commonfns();
                sc.Language = "VBScript";
                string rpath = file.Substring(0, file.LastIndexOf('\\'));
                sc.ExecuteStatement("TestRunnerPath=\"" + rpath + "\"");
                string code;
               string ScriptFileText;
               string ScriptFileTextPath;

               ScriptFileTextPath = func.getActualFilePath("..\\..\\..\\TestCases\\AutoTests\\VisualTest\\ModelCenter\\Command_Lib\\Include_Files\\InitializeGlobalVar.inc");
               ScriptFileText = System.IO.File.ReadAllText(ScriptFileTextPath);

                string testBeginning;

                testBeginning = "Dim lib, sh, cst " + Environment.NewLine;
                testBeginning = testBeginning + "Set cst = CreateObject( \"MCConstants.wsc\" ) " + Environment.NewLine;
                testBeginning = testBeginning + "Set sh = CreateObject( \"WScript.Shell\" )" + Environment.NewLine;
                testBeginning = testBeginning + "Set lib = CreateObject( \"TestFunctions.Functions.WSC\" )" + Environment.NewLine;
                testBeginning = testBeginning + "testName = \"" + file.Substring(file.LastIndexOf('\\') + 1) + "\" " + Environment.NewLine;
                testBeginning = testBeginning + "lib.SetTestName testName " + Environment.NewLine;



                code = ScriptFileText + testBeginning + System.IO.File.ReadAllText(file);

                sc.Timeout = 300000;
                try
                {
                    sc.AddCode(code);
                }
                catch (Exception e)
                {
                    string msg = e.ToString() + "\n" +
                       "Description: " + sc.Error.Description + "\n" +
                       "Source: " + sc.Error.Source + "\n" +
                       "Line: " + sc.Error.Line + "\n" +
                       "Text: " + sc.Error.Text + "\n" +
                       "Number: " + sc.Error.Number;
                    Assert.Fail(msg);
                }
            }

             static public IEnumerable<TestCaseData> testList_Interactive_Tests_Priority_A
            {
                get
                {
                    commonfns func = new commonfns();
                    string testDir = func.getActualDirectoryPath("..\\..\\..\\TestCases\\AutoTests\\VisualTest\\ModelCenter\\Test_Case_Files\\Interactive_Tests\\Priority_A");
                    IEnumerable<string> files = System.IO.Directory.GetFiles(testDir, "*.vbs");
                    foreach (string f in files)
                    {
                        string testName = f.Substring(f.LastIndexOf('\\') + 1);
                        testName = testName.Substring(0, testName.Length - 4);
                        yield return new TestCaseData(f).SetName(testName);
                    }
                }
            }
      */

      [Test, TestCaseSource("testList_TestComponentPlugIns")]
       public void VisualTest_TestComponentPlugIns(string file)
      {
          commonfns func = new commonfns();
          sc.Language = "VBScript";
          string rpath = file.Substring(0, file.LastIndexOf('\\'));
          sc.ExecuteStatement("TestRunnerPath=\"" + rpath + "\"");
          string code;
          string ScriptFileText;
          string ScriptFileTextPath;

          ScriptFileTextPath = func.getActualFilePath("..\\..\\..\\TestCases\\AutoTests\\VisualTest\\ModelCenter\\Command_Lib\\Include_Files\\InitializeGlobalVar.inc");
          ScriptFileText = System.IO.File.ReadAllText(ScriptFileTextPath);

          string testBeginning;

          testBeginning = "Dim lib, sh, cst " + Environment.NewLine;
          testBeginning = testBeginning + "Set cst = CreateObject( \"MCConstants.wsc\" ) " + Environment.NewLine;
          testBeginning = testBeginning + "Set sh = CreateObject( \"WScript.Shell\" )" + Environment.NewLine;
          testBeginning = testBeginning + "Set lib = CreateObject( \"TestFunctions.Functions.WSC\" )" + Environment.NewLine;
          testBeginning = testBeginning + "testName = \"" + file.Substring(file.LastIndexOf('\\') + 1) + "\" " + Environment.NewLine;
          testBeginning = testBeginning + "lib.SetTestName testName " + Environment.NewLine;



          code = ScriptFileText + testBeginning + System.IO.File.ReadAllText(file);

          sc.Timeout = 300000;
          try
          {
              sc.AddCode(code);
          }
          catch (Exception e)
          {
              string msg = e.ToString() + "\n" +
                 "Description: " + sc.Error.Description + "\n" +
                 "Source: " + sc.Error.Source + "\n" +
                 "Line: " + sc.Error.Line + "\n" +
                 "Text: " + sc.Error.Text + "\n" +
                 "Number: " + sc.Error.Number;
              Assert.Fail(msg);
          }
      }

       static public IEnumerable<TestCaseData> testList_TestComponentPlugIns
      {
          get
          {
              commonfns func = new commonfns();
              string testDir = func.getActualDirectoryPath("..\\..\\..\\TestCases\\AutoTests\\VisualTest\\ModelCenter\\Test_Case_Files\\TestComponentPlugIns");
              IEnumerable<string> files = System.IO.Directory.GetFiles(testDir, "*.vbs");
              foreach (string f in files)
              {
                  string testName = f.Substring(f.LastIndexOf('\\') + 1);
                  testName = testName.Substring(0, testName.Length - 4);
                  yield return new TestCaseData(f).SetName(testName);
              }
          }
      }



   }
}
