################################################################################
# Automatically-generated file. Do not edit!
################################################################################

%.o: ../%.c subdir.rule.mk
	@echo 'Building file: $<'
	@echo 'Invoking: MBS30.compiler.gnu.c'
	gcc -O0 -g3 -Wall -c -fmessage-length=0 -v -o "$@" "$<" && \
	echo -n '$(@:%.o=%.d)' $(dir $@) > '$(@:%.o=%.d)' && \
	gcc -MM -MG -P -w -O0 -g3 -Wall -c -fmessage-length=0 -v   "$<" >> '$(@:%.o=%.d)'
	@echo 'Finished building: $<'
	@echo ' '

