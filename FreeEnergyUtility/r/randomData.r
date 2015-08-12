alpha=10
len=16
x = c(1:len)
y = c(0:(alpha-1)/alpha)
G = matrix(runif(length(x)*length(y),min=0,max=1), ncol=length(y),nrow=length(x))
G[1,ncol(G)]=0

rownames(G) = x
colnames(G) = y
write.table(t(G),"../dat/random.dat",sep="\t",col.names=NA)

bin = c((1*alpha):(len*alpha))

A=matrix(0,nrow=length(bin)*length(bin),ncol=3)
A[,1]=rep(bin,each=length(bin))
A[,2]=rep(bin,length(bin))
A[,3]=runif(length(bin)*length(bin),min=0,max=1000)
write.table(A,"../dat/select_random.dat",sep="\t", row.names=FALSE, col.names=FALSE)