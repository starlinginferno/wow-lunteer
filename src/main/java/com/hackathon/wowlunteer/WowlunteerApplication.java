package com.hackathon.wowlunteer;

import com.hackathon.wowlunteer.eventType.persistence.model.EventType;
import com.hackathon.wowlunteer.eventType.service.EventTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class WowlunteerApplication implements CommandLineRunner {

    private EventTypeService eventTypeService;

    @Autowired
    public WowlunteerApplication(EventTypeService eventTypeService) {
        this.eventTypeService = eventTypeService;
    }

    public static void main(String[] args) {
        SpringApplication.run(WowlunteerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if(eventTypeService.findAll().isEmpty()) {
            List<EventType> evetTypes = Arrays.asList(
                    new EventType("Fundraising and Administration", "Many charities are looking for people to support office work including fundraising and administration. This can be a great insight into how a charity works and your role could be very varied from helping writing funding proposals to communications and marketing work."),
                    new EventType("Environmental", "Whether it be climate change campaigning, water way cleaning or animals and plants you are interested in there is a range of environmental opportunities. Many are one off opportunities with less commitment, though more substantial student led projects are available."),
                    new EventType("Children and Young People", "There are lots of opportunities to volunteer with young people, whether within a primary or secondary school or with youth groups around the capital. Opportunities can include mentoring, training and workshops on different areas and fun activities with disabled children."),
                    new EventType("Elderly and disabled", "Volunteer with elderly people and disabled, be it helping them with their shopping, chatting to them about days gone by and playing chess."),
                    new EventType("Homeless", "Many LSE students are interested in volunteering with homeless groups are looking to set up student projects to support homeless people close to campus. Find out about setting up your own volunteering project and search for opportunities with London based and national homeless charities on the vacancy board."),
                    new EventType("Trustee", "Becoming a trustee is an excellent position to have on your CV, whilst also offering advice and support to a charity you are interested in. Trustee roles can vary from advisory roles to more hands on roles volunteering with the charity. Further information can be found on the trustee page."),
                    new EventType("International", "There are hundreds of opportunities to volunteer abroad. For further details on organisations and tips for searching for international opportunities visit our opportunities abroad pages."),
                    new EventType("Research", "Whether you are looking for research linked with your dissertation or thesis or interested in learning more about the work of a charity then you could do some bespoke research for them. There are several charities that have shown an interest in students contacting them about research. For further information contact the Volunteer Coordinator."),
                    new EventType("Refugees/migrants", "Opportunities volunteering with refugee and migrant workers can vary from lunch and dinner time support, language skills and pro bono legal support, amongst other things."));

            for (EventType e : evetTypes) {
                eventTypeService.save(e);
            }
        }
    }
}
