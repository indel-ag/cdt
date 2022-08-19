################################################################################
# Automatically-generated file. Do not edit!
################################################################################

f1.c: ../test.tar f1.c.mk
	@echo 'Building file: $<'
	@echo 'Invoking: Un-tar'
	tar -xf "$<"
	@echo 'Finished building: $<'
	@echo ' '

f2.c: f1.c
test_ar.h: f1.c
CDT.bmp: f1.c

