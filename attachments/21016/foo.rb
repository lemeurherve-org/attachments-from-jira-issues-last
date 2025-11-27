def example seq, name
  jname = name.split(/-/).map {|x| x.capitalize}.join("")
  puts "
@Test
public void test#{jname}() throws IOException {
  assertThat(colorize(\"#{seq}hello world\"), is(\"<span class=\\\"ansicolor-#{name}\\\">hello world</span>\"));
}
"
end

# [0m reset; clears all colors and styles (to white on black)
example "\e1m" , "bold"
example "\e3m" , "italics"
example "\e4m" , "underline"
example "\e9m" , "strikethrough"

example "\e30m", "black"
example "\e31m", "red"
example "\e32m", "green"
example "\e33m", "yellow"
example "\e34m", "blue"
example "\e35m", "magenta"
example "\e36m", "cyan"
example "\e37m", "white"

example "\e40m", "background-black"
example "\e41m", "background-red"
example "\e42m", "background-green"
example "\e43m", "background-yellow"
example "\e44m", "background-blue"
example "\e45m", "background-magenta"
example "\e46m", "background-cyan"
example "\e47m", "background-white"
