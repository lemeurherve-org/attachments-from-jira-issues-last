import com.sun.btrace.AnyType;
import com.sun.btrace.annotations.*;
import static com.sun.btrace.BTraceUtils.*;

@BTrace
class TraceBouncyCastleProviderConstructor {
	@OnMethod(clazz = "org.bouncycastle.jce.provider.BouncyCastleProvider", method = "<init>")
	void onInit(@Self java.lang.Object self) {
		println();
		print(">>> [");
		print(timestamp());
		print("] new BouncyCastleProvider: ");
		print(str(self));
		println();
		jstack();
		println();
	}
}

