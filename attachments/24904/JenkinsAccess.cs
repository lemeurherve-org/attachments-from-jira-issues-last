using System;
using System.IO;
using System.Net;
using System.Text;


public class JenkinsAccess {
    static void Main(string[] args) {
        var req = HttpWebRequest.Create(args[0]);
        var res = (HttpWebResponse)req.GetResponse();

        Console.WriteLine("[complete]");
        Console.Write((int)res.StatusCode);
        Stream st = res.GetResponseStream();
        StreamReader sr = new StreamReader(st);
        string html = sr.ReadToEnd();
        sr.Close();
        st.Close();

        Console.WriteLine(html);
	}
}

