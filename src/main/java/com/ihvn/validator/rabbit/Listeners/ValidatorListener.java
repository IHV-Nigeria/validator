package com.ihvn.validator.rabbit.Listeners;

import com.ihvn.validator.models.Container;
import com.ihvn.validator.rabbit.QueueNames;
import com.ihvn.validator.serviceImpl.FileValidatorImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ValidatorListener {
    private final FileValidatorImpl fileValidator;

    public ValidatorListener(FileValidatorImpl fileValidator) {
        this.fileValidator = fileValidator;
    }

    // This is a rabbit listener the listens and consume any message that comes to the queue
    @RabbitListener(queues = QueueNames.VALIDATOR_QUEUE)
    public void validatorListenerOne(List<Container> containerList){
        log.info("VALIDATOR 1 {}", containerList.size());
        // using parallel stream for run the validation process in parallel
        containerList.stream().forEach(container -> {
            // Call the file validation method to validate this container
            fileValidator.validationFile(container);
        });
    }

    @RabbitListener(queues = QueueNames.VALIDATOR_QUEUE)
    public void validatorListenerTwo(List<Container> containerList){
        log.info("VALIDATOR 2 {}", containerList.size());
        // using parallel stream for run the validation process in parallel
        containerList.stream().forEach(container -> {
            // Call the file validation method to validate this container
            fileValidator.validationFile(container);
        });
    }
}
