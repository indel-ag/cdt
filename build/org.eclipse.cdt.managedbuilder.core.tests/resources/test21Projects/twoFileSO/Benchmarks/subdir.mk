################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
C_SRCS += \
../so1.c \
../so2.c 

C_DEPS += \
./so1.d \
./so2.d 

OBJS += \
./so1.o \
./so2.o 


# Each subdirectory must supply rules for building sources it contributes
include subdir.rule.mk

clean: clean--2e-

clean--2e-:
	-$(RM) ./so1.d ./so1.o ./so2.d ./so2.o

.PHONY: clean--2e-

