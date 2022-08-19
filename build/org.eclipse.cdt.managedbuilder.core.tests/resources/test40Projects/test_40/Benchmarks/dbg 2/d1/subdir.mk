################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
CPP_SRCS += \
../d1/q.cpp \
../d1/u.cpp \
../d1/w.cpp 

CPP_DEPS += \
./d1/q.d \
./d1/u.d \
./d1/w.d 

OBJS += \
./d1/q.o \
./d1/u.o \
./d1/w.o 


# Each subdirectory must supply rules for building sources it contributes
include d1/subdir.rule.mk

clean: clean-d1

clean-d1:
	-$(RM) ./d1/q.d ./d1/q.o ./d1/u.d ./d1/u.o ./d1/w.d ./d1/w.o

.PHONY: clean-d1

