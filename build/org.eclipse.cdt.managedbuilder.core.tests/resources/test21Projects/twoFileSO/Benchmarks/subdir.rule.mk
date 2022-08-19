################################################################################
# Automatically-generated file. Do not edit!
################################################################################

%.o: ../%.c subdir.rule.mk
	@echo 'Building file: $<'
	@echo 'Invoking: compiler.gnu.cpp'
	g++ -DXXX -O0 -g3 -Wall -c -o "$@" "$<" && \
	echo -n '$(@:%.o=%.d)' $(dir $@) > '$(@:%.o=%.d)' && \
	g++ -MM -MG -P -w -DXXX -O0 -g3 -Wall -c   "$<" >> '$(@:%.o=%.d)'
	@echo 'Finished building: $<'
	@echo ' '

