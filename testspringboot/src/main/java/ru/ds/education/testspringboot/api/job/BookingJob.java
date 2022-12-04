package ru.ds.education.testspringboot.api.job;

import org.springframework.beans.factory.annotation.Autowired;
import ru.ds.education.testspringboot.core.service.TovarService;

public class BookingJob extends Thread
{
    private final Long idUser;

    @Autowired
    private final TovarService tovarService;

    public BookingJob(Long idUser, TovarService tovarService){
        this.idUser = idUser;
        this.tovarService = tovarService;
    }


    @Override
    public void run()
    {
        try{
            sleep(5 * 60 * 1000);
        }catch(InterruptedException e){}

        try {
            tovarService.deBook(idUser);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}