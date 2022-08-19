rcbsBasicTest.o: ../rcbsBasicTest.c rcbsBasicTest.o.mk
	@echo 'Building file: $<'
	@echo 'Now executing custom build step for rcbsBasicTest debug config'
	gcc -g -c ../rcbsBasicTest.c -o ./rcbsBasicTest.o
	@echo 'Finished building: $<'
	@echo ' '

