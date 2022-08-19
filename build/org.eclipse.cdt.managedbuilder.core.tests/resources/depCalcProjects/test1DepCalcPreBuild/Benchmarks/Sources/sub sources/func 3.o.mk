Sources/sub\ sources/func\ 3.o: ../Sources/sub\ sources/func\ 3.c Sources/sub\ sources/func\ 3.o.mk
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C Compiler'
	gcc -I../Headers -I../Sources/sub\ sources -O0 -g3 -Wall -c -fmessage-length=0 -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '

Sources/sub\ sources/func\ 3.d: ../Sources/sub\ sources/func\ 3.c Sources/sub\ sources/func\ 3.o.mk
	@echo 'Regenerating dependency file: $@'
	gcc -w -MM -MP -MT"Sources/sub\ sources/func\ 3.d" -MT"Sources/sub\ sources/func\ 3.o" -I../Headers -I../Sources/sub\ sources -MF "$@" "$<"
	@echo ' '

