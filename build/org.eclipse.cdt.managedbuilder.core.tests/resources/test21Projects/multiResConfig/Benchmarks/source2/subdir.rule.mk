################################################################################
# Automatically-generated file. Do not edit!
################################################################################

source2/%.o: ../source2/%.cpp source2/subdir.rule.mk
	@echo 'Building file: $<'
	@echo 'Invoking: compiler.gnu.cpp'
	g++ -I../headers -O0 -g3 -Wall -c -fmessage-length=0 -o "$@" "$<" && \
	echo -n '$(@:%.o=%.d)' $(dir $@) > '$(@:%.o=%.d)' && \
	g++ -MM -MG -P -w -I../headers -O0 -g3 -Wall -c -fmessage-length=0   "$<" >> '$(@:%.o=%.d)'
	@echo 'Finished building: $<'
	@echo ' '

