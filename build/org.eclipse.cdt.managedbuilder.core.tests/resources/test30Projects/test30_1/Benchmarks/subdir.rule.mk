################################################################################
# Automatically-generated file. Do not edit!
################################################################################

%.oprestripped: ./%.c subdir.rule.mk
	@echo 'Building file: $<'
	@echo 'Invoking: MBS30.compiler.gnu.c'
	gcc -O2 -g -Wall -c -fmessage-length=0 -v -o "$@" "$<" && \
	echo -n '$(@:%.oprestripped=%.d)' $(dir $@) > '$(@:%.oprestripped=%.d)' && \
	gcc -MM -MG -P -w -O2 -g -Wall -c -fmessage-length=0 -v   "$<" >> '$(@:%.oprestripped=%.d)'
	@echo 'Finished building: $<'
	@echo ' '

%.o: ./%.oprestripped subdir.rule.mk
	@echo 'Building file: $<'
	@echo 'Invoking: Strip object file'
	strip --preserve-dates -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '

%.jpeg: ./%.bmp subdir.rule.mk
	@echo 'Building file: $<'
	@echo 'Invoking: Convert to jpeg'
	cjpeg  -outfile "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '

