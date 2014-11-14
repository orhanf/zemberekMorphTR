zemberekMorphTR
===============

This repo provides a way for morhological segmentation of Turkish corpora by using Zemberek.

The segmentations are done as a preprocessing step for Neural Machine Translation, Eng-Tr and Tr-Eng.

There is also a jar provided for direct usage ZemberekJar.jar, only works with Java>=7. Usage of the jar is as follows:
java -jar zemberekJar -i <input_file> -o <output_file> -d -s
  -d : disambiguate
  -s : short suffix list

