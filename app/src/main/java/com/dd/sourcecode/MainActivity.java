package com.dd.sourcecode;

import android.app.*;
import android.content.*;
import android.os.*;
import android.webkit.*;
import android.widget.*;
import android.view.View.*;
import android.view.*;

public class MainActivity extends Activity implements OnClickListener
{
	private WebView webview;
	public void onClick(View v){

		switch(v.getId()){
			case R.id.goBtn:
				EditText searchBar = (EditText) findViewById(R.id.searchBar);
				webview.loadUrl("http://" + searchBar.getText().toString());
				break;
		}
	}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        webview = (WebView) findViewById(R.id.browser);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.addJavascriptInterface(new MyJavaScriptInterface(this), "HtmlViewer");

        webview.setWebViewClient(new WebViewClient() {
				@Override
				public void onPageFinished(WebView view, String url) {
					webview.loadUrl("javascript:window.HtmlViewer.showHTML" +
									"('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
				}
			});
		Button goBtn = (Button) findViewById(R.id.goBtn);
		goBtn.setOnClickListener(this);
        webview.loadUrl("http://google.com");
    }

    class MyJavaScriptInterface {

        private Context ctx;

        MyJavaScriptInterface(Context ctx) {
            this.ctx = ctx;
        }
		
		@JavascriptInterface
        public void showHTML(String html) {
			new AlertDialog.Builder(ctx).setTitle("Source Code").setMessage(html)
				.setPositiveButton(android.R.string.ok, null).setCancelable(false).create().show();
        }

    }
}
