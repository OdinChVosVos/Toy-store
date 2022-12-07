package ru.ds.education.testspringboot.api.job;

import org.springframework.beans.factory.annotation.Autowired;
import ru.ds.education.testspringboot.core.service.TovarService;

public class BookingJob extends Thread
{
    private final Long idUser;

    @Autowired
    private final TovarService tovarService;

    private Long timeExpire;

    public BookingJob(Long idUser, TovarService tovarService, Long timeExpire){
        this.idUser = idUser;
        this.tovarService = tovarService;
        this.timeExpire = timeExpire;
    }


    @Override
    public void run()
    {

        try{
            sleep(timeExpire * 1000);
        }catch(InterruptedException e){}

        try {
            tovarService.deBook(idUser);
            System.out.println("1010101010101010101010");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}