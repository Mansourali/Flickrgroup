package training.mansour.beautifullibya.Services;

import android.os.AsyncTask;
import android.support.v4.os.AsyncTaskCompat;
import android.widget.Toast;

import me.tatarka.support.job.JobParameters;
import me.tatarka.support.job.JobService;

/**
 * Created by Mansour on 28/01/2016.
 */
public class MyService extends JobService {

    @Override
    public boolean onStartJob(JobParameters params) {
        new myTask(this).execute(params);
        Toast.makeText(this, "JOB START", Toast.LENGTH_LONG).show();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    private class myTask extends AsyncTask<JobParameters, Void, JobParameters> {
        MyService myService;

        myTask (MyService myService){
            this.myService = myService ;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JobParameters doInBackground(JobParameters... jobParameterses) {
            return jobParameterses[0];
        }

        @Override
        protected void onPostExecute(JobParameters jobParameters) {
            super.onPostExecute(jobParameters);
            myService.jobFinished(jobParameters, false);
        }
    }
}
