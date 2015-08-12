## Runs FreeEnergyError.r from command line arguments specified in dat/in/readfile.dat
## Scott Gigante, scottgigante@gmail.com, December 2014

filename = "../dat/in/readfile.dat"
args = scan(file=filename, what=character(),sep="\t", flush=TRUE, quiet=TRUE)
commandArgs = function(trailingOnly) args
source("FreeEnergyUtility.r")
