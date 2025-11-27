job('example1') {
    steps
    {
        batchFile('ping 127.0.0.1 -n 10 > nul')
    }
}

job('example2') {
    blockOn('example1')
    {
        blockLevel('GLOBAL')
        scanQueueFor('DISABLED')
    }
    
    steps
    {
        batchFile('ping 127.0.0.1 -n 10 > nul')
    }   
}
