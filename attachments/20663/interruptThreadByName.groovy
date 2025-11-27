import java.lang.management.*;

String targetThreadName = "Handling POST /view/RM%20+%20PL/view/RM%20+%20PL%208.2/job/agent-framework820/doDelete : http-18443-29";

ThreadGroup tg = Thread.currentThread( ).getThreadGroup( );
ThreadGroup ptg;
while ( (ptg = tg.getParent( )) != null )
	tg = ptg;
rootThreadGroup = tg;


ThreadGroup root = rootThreadGroup
println("Root Thread group is " + root)

ThreadMXBean thbean = ManagementFactory.getThreadMXBean( );
int nAlloc = thbean.getThreadCount( );
println("nAlloc is " + nAlloc)


int n = 0;
Thread[] threads = null;

boolean shouldExecute = true

while (shouldExecute){
	nAlloc *= 2;
	threads = new Thread[ nAlloc ];
	n = root.enumerate( threads, true );
	if (n < nAlloc){
		shouldExecute = false;
	}
}

println("New nAlloc size is " + nAlloc)
println("we found " + n + " threads total")


for (Thread thread : threads){

	if (thread == null){
		continue;
	}
	
	if (targetThreadName.equals(thread.getName())){
		println("we found our thread")
		thread.interrupt();
		println("we have interrupted our thread.");
	}
}
		