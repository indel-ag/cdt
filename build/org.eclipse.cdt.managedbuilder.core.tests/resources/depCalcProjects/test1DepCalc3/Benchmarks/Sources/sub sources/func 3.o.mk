################################################################################
# Automatically-generated file. Do not edit!
################################################################################

Sources/sub\ sources/func\ 3.o: ../Sources/sub\ sources/func\ 3.c Sources/sub\ sources/func\ 3.o.mk
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C Compiler'
	gcc -DFUN3 -I../Headers -I../Sources/sub\ sources -O0 -g3 -Wall -c -fmessage-length=0 -o "$@" "$<" && \
	echo -n '$(@:%.o=%.d)' $(dir $@) > '$(@:%.o=%.d)' && \
	gcc -MM -MG -P -w -DFUN3 -I../Headers -I../Sources/sub\ sources -O0 -g3 -Wall -c -fmessage-length=0   "$<" >> '$(@:%.o=%.d)'
	@echo 'Finished building: $<'
	@echo ' '

