package tn.esprit.autoloc.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.service.JournalisationService;

@Slf4j
@Service
public class JournalisationServiceImpl implements JournalisationService {

    @Override
    public void log(String message) {
        log.info(message);
        System.out.println(message);
    }
}
