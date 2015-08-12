
DAT_PREFIX = "../dat/"
filename = "../dat/readfile.dat"
alpha_scale = 10

args = scan(file=filename, what=character(),sep="\t", flush=TRUE, quiet=TRUE)

for (i in 8:length(args)) {
  A = read.table(paste0(DAT_PREFIX,args[i]))  
  
  B = matrix(0, nrow=nrow(A),ncol=5)
  B[,1] = floor(A[,1]/alpha_scale)
  B[,2] = (A[,1]%%alpha_scale)/alpha_scale
  B[,3] = floor(A[,2]/alpha_scale)
  B[,4] = (A[,2]%%alpha_scale)/alpha_scale
  B[,5] = A[,3]
  
  #d = 100
  #B[,1]=B[,1]/d
  #B[,3]=B[,3]/d
  
  write.table(B,paste0(DAT_PREFIX,"mod_",args[i]),sep="\t",row.names=FALSE,col.names=FALSE)
}