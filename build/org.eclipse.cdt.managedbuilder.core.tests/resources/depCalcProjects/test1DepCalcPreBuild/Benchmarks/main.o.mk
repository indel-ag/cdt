################################################################################
# Automatically-generated file. Do not edit!
################################################################################

main.o: ../main.c main.o.mk
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C Compiler'
	gcc -I../Headers -I../Sources/sub\ sources -O0 -g3 -c -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '

main.d: ../main.c main.o.mk
	@echo 'Regenerating dependency file: $@'
	gcc -w -MM -MP -MT"main.d" -MT"main.o" -I../Headers -I../Sources/sub\ sources -MF "$@" "$<"
	@echo ' '

