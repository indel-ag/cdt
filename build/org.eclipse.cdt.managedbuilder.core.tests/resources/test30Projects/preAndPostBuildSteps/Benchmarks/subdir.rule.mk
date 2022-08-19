################################################################################
# Automatically-generated file. Do not edit!
################################################################################

%.o: ../%.cxx subdir.rule.mk
	@echo 'Building file: $<'
	@echo 'Invoking: MBS30.compiler.gnu.cpp'
	g++ -O0 -g3 -Wall -c -fmessage-length=0 -o "$@" "$<" && \
	echo -n '$(@:%.o=%.d)' $(dir $@) > '$(@:%.o=%.d)' && \
	g++ -MM -MG -P -w -O0 -g3 -Wall -c -fmessage-length=0   "$<" >> '$(@:%.o=%.d)'
	@echo 'Finished building: $<'
	@echo ' '

