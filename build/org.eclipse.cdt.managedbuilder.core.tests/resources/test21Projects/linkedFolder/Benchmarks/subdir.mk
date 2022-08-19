################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
C_SRCS += \
../f1.c \
../f2.c 

C_DEPS += \
./f1.d \
./f2.d 

OBJS += \
./f1.o \
./f2.o 


# Each subdirectory must supply rules for building sources it contributes
include subdir.rule.mk

clean: clean--2e-

clean--2e-:
	-$(RM) ./f1.d ./f1.o ./f2.d ./f2.o

.PHONY: clean--2e-

