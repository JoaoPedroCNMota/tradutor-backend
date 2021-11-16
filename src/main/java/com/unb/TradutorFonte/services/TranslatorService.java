package com.unb.TradutorFonte.services;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.polly.AmazonPollyClient;
import com.amazonaws.services.polly.AmazonPollyClientBuilder;
import com.amazonaws.services.polly.model.*;
import com.sun.java.accessibility.util.Translator;
import com.unb.TradutorFonte.domain.Code;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Component
@RequiredArgsConstructor
public class TranslatorService {

    private AmazonPollyClient polly;
    private Voice voice;

    public String codeToPseudocode(Code code){
        String[] splitCode = code.getCode().split(";");

        for (String part : splitCode){

        }
        return null;
    }

    public InputStream synthesize(String text, OutputFormat format) throws IOException {

        polly = (AmazonPollyClient) AmazonPollyClientBuilder.standard().withRegion(Regions.SA_EAST_1).build();

        // Create describe voices request.
        DescribeVoicesRequest describeVoicesRequest = new DescribeVoicesRequest();

        // Synchronously ask Amazon Polly to describe available TTS voices.
        DescribeVoicesResult describeVoicesResult = polly.describeVoices(describeVoicesRequest);
        voice = describeVoicesResult.getVoices().stream().filter(
                v -> v.getLanguageCode().contains("BR") ).collect(Collectors.toList()).get(2);

        SynthesizeSpeechRequest synthReq = new SynthesizeSpeechRequest().withText(text).withVoiceId(voice.getId())
                        .withOutputFormat(format);
        SynthesizeSpeechResult synthRes = polly.synthesizeSpeech(synthReq);

        return synthRes.getAudioStream();
    }

    public void speechCode(String text) throws IOException, JavaLayerException {

        //Sintetizando fala a partir de objeto
        InputStream speechStream = synthesize(text, OutputFormat.Mp3);

        //Criando MP3Player para reprodução de voz;
        AdvancedPlayer player = new AdvancedPlayer(speechStream,
                javazoom.jl.player.FactoryRegistry.systemRegistry().createAudioDevice());

        player.setPlayBackListener(new PlaybackListener() {
            @Override
            public void playbackStarted(PlaybackEvent evt) {
                System.out.println("Reproduzindo voz");
            }

            @Override
            public void playbackFinished(PlaybackEvent evt) {
                System.out.println("Fim da reprodução");
            }
        });

        player.play();
    }

}
