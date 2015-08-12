## Writes and reads logs of variables in R
## Scott Gigante, scottgigante@gmail.com, Dec 2014

INPUT_PREFIX = "../dat/"

writeLog = function(M,name=deparse(substitute(M))) {
  write.table(M,file=paste0(INPUT_PREFIX,name,".log"),sep="\t",col.names=F,row.names=F)
  return(M)
}

readLog = function(M) {
  A = as.matrix(read.table(paste0(INPUT_PREFIX,deparse(substitute(M)),".log")))
  if (ncol(A) == 1) {
    A = as.vector(A)
  }
  return(A)
}
